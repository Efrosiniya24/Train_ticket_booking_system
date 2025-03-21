import { FaTrash } from "react-icons/fa"; 
import SideBarAdmin from "../../components/sideBar/sideBar";  
import style from "./route.module.css"; 
import { useEffect, useState } from "react"; 
import axios from "axios"; 
import Add from "../../components/add_plus/add";
import commonStyle from "../styles/forAllPAges.module.css"

const RoutePage = () => {
    const [routes, setRoutes] = useState([]); 
    const [isLoading, setIsLoading] = useState(false); 
    const [error, setError] = useState(null);
    const [isAddVisible, setIsAddVisible] = useState(false);
    const [isStationVisible, setIsStationVisible] = useState(false);
    const [stations, setStations] = useState([{ station: "", departureDate: "", arrivalDate: "" }]); 
    const [allStations, setAllStations] = useState([]);
    const [allTrains, setAllTrains] = useState([]); 
    const [selectedTrain, setSelectedTrain] = useState("");
    const [newStationName, setNewStationName] = useState("");
    const [hoveredRow, setHoveredRow] = useState(null);
    // const [selectedRoute, setSelectedRoute] = useState("");
    const [selectedRouteId, setSelectedRouteId] = useState(null);
    const [selectedRoute, setSelectedRoute] = useState(null);


    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);
            setError(null);
            try {
                const token = localStorage.getItem("accessToken");
    
                const results = await Promise.allSettled([
                    axios.get("http://localhost:8080/route/allRoutes", {
                        headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
                    }),
                    axios.get("http://localhost:8080/station/allStations", {
                        headers: { Authorization: `Bearer ${token}` },
                    }),
                    axios.get("http://localhost:8080/train/allTrains", {
                        headers: { Authorization: `Bearer ${token}` },
                    }),
                ]);

                if (results[0].status === "fulfilled") {
                    setRoutes(results[0].value.data);
                } else {
                    console.error("Ошибка загрузки маршрутов:", results[0].reason);
                }
    
                if (results[1].status === "fulfilled") {
                    setAllStations(results[1].value.data);
                } else {
                    console.error("Ошибка загрузки станций:", results[1].reason);
                }
                
                if (results[2].status === "fulfilled") {
                    setAllTrains(results[2].value.data);
                } else {
                    console.error("Ошибка загрузки поездов:", results[2].reason);
                }

            } catch (error) {
                console.error("Ошибка при загрузке данных:", error);
                setError(error);
            } finally {
                setIsLoading(false);
            }
        };
    
        fetchData();
    }, []);
    

    const toggleAddVisibility = () => {
        setIsAddVisible(!isAddVisible);
    };

    const toggleStationVisibility = () => {
        setIsStationVisible(!isStationVisible);
    };

    const addStation = () => {
        const lastStation = stations[stations.length - 1];
        if (!lastStation.station || !lastStation.departureDate || !lastStation.arrivalDate) {
            alert("Заполните все поля перед добавлением новой станции!");
            return;
        }

        setStations([...stations, { station: "", departureDate: "", arrivalDate: "" }]);
    };

    const removeStation = (index) => {
        if (stations.length === 1) {
            alert("Нельзя удалить последнюю станцию!");
            return;
        }

        setStations(stations.filter((_, i) => i !== index));
    };

    const handleSaveRoute = async () => {
        if (!selectedTrain || stations.length < 2) {
            alert("Выберите поезд и укажите хотя бы две станции!");
            return;
        }
    
        const token = localStorage.getItem("accessToken");

        const routeDTO = {
            train: { id: selectedTrain },
            routeStationTimeDTO: stations.map((station, index) => ({
                stopOrder: index + 1,
                departureDate: station.departureDate,
                arrivalDate: station.arrivalDate,
                stationDTO: { name: station.station }
            }))
        };
    
        try {
            const response = await axios.post("http://localhost:8080/route/create", routeDTO, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" }
            });
            
            alert("Маршрут успешно создан!");
            setRoutes([...routes, response.data]);
            setIsAddVisible(false);
            setStations([{ station: "", departureDate: "", arrivalDate: "" }]); 
            setSelectedTrain(""); 
    
        } catch (error) {
            console.error("Ошибка при сохранении маршрута:", error);
            alert("Ошибка при сохранении маршрута!");
        }
    };

    const handleSaveStation = async () => {
        if (!newStationName.trim()) {
            alert("Введите название станции!");
            return;
        }
    
        const token = localStorage.getItem("accessToken");
    
        try {
            const response = await axios.post(
                "http://localhost:8080/station/add",
                { name: newStationName },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json"
                    }
                }
            );
    
            alert("Станция успешно добавлена!");

            setAllStations([...allStations, { id: response.data.id, name: newStationName }]);
            setNewStationName(""); 
    
        } catch (error) {
            console.error("Ошибка при сохранении станции:", error);
            alert("Ошибка при сохранении станции!");
        }
    };
    const handleDeleteRoute = async (routeId) => {
        if (!window.confirm("Вы уверены, что хотите удалить этот маршрут?")) {
            return;
        }

        try {
            const token = localStorage.getItem("accessToken");
            await axios.delete(`http://localhost:8080/route/delete/${routeId}`, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            });

            setRoutes(routes.filter(route => route.id !== routeId));
        } catch (error) {
            console.error("Ошибка при удалении поезда:", error);
            alert("Ошибка при удалении поезда!");
        }
    };

    const handleDeleteTrain = async (stationId) => {
        if (!window.confirm("Вы уверены, что хотите удалить этот поезд?")) {
            return;
        }

        try {
            const token = localStorage.getItem("accessToken");
            await axios.delete(`http://localhost:8080/station/delete/${stationId}`, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            }); 

            setStations(stations.filter(station => station.id !== stationId));
        } catch (error) {
            console.error("Ошибка при удалении поезда:", error);
            alert("Ошибка при удалении поезда!");
        }
    };

    const selectedTrainObj = allTrains.find(train => train.id === selectedTrain);

    const handleUpdateRoute = async () => {
        if (!selectedTrain || stations.length < 2) {
            alert("Выберите поезд и укажите хотя бы две станции!");
            return;
        }
    
        const token = localStorage.getItem("accessToken");
    
        const updatedRouteDTO = {
            id: selectedRoute.id,
            train: {
                id: selectedTrain,
                train: selectedTrainObj ? selectedTrainObj.train : ""
            },
            routeStationTimeDTO: stations.map((station, index) => ({
                id: selectedRoute.routeStationTimeDTO[index]?.id || null, 
                stopOrder: index + 1,
                departureDate: station.departureDate,
                arrivalDate: station.arrivalDate,
                stationDTO: {
                    id: allStations.find(s => s.name === station.station)?.id || null, 
                    name: station.station
                }
            }))
        };
        
    
        try {
            const response = await axios.put(
                `http://localhost:8080/route/update/${selectedRoute.id}`, 
                updatedRouteDTO, 
                {
                    headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" }
                }
            );
    
            alert("Маршрут успешно обновлён!");

            setRoutes(routes.map(route => 
                route.id === selectedRoute.id 
                    ? { ...response.data, routeStationTimeDTO: response.data.routeStationTimeDTO }
                    : route
            ));

            setSelectedRoute(response.data);

            setStations(response.data.routeStationTimeDTO.map(stationTime => ({
                id: stationTime.id, 
                station: stationTime.stationDTO.name,
                departureDate: stationTime.departureDate,
                arrivalDate: stationTime.arrivalDate
            })));

            setSelectedTrain({
                id: response.data.train.id,
                train: response.data.train.train
            });
    
            setIsAddVisible(false); 
            setStations([{ station: "", departureDate: "", arrivalDate: "" }]); 
            setSelectedTrain(""); 
            setSelectedRoute(null);
    
        } catch (error) {
            console.error("Ошибка при обновлении маршрута:", error);
            alert("Ошибка при обновлении маршрута!");
        }
    };
    
    
    const handleRowClick = (route) => {
        setSelectedRouteId(route.id);
        setSelectedTrain(route.train.id);
        setIsAddVisible(true);
        setStations(route.routeStationTimeDTO.map(stationTime => ({
            id: stationTime.id, 
            station: stationTime.stationDTO.name,
            departureDate: stationTime.departureDate,
            arrivalDate: stationTime.arrivalDate
        })));
        
        setSelectedRoute(route); 
    };
    

    return ( 
        <div>
            <SideBarAdmin/>
            <div className={commonStyle.containerAdmin}>
                <div className={commonStyle.row}>
                    <p className={style.stations} onClick={toggleStationVisibility}>Станции</p>
                    <h1 className={style.routes}>Маршруты</h1>
                </div>
                <div className={commonStyle.secondPart}>
                    <Add onClick={toggleAddVisibility}/>
                    <table className={commonStyle.tableRoute}>        
                        <thead className={commonStyle.theadRoute}>
                            <tr>
                                <th>ID</th>
                                <th>Маршрут</th>
                                <th>Поезд</th>
                                <th>Отправление</th>
                                <th>Прибытие</th>
                                <th></th>
                            </tr>
                        </thead>   
                        <tbody>
                            {routes.map((route, index) => (
                                <tr 
                                key={route.id} 
                                onClick={() => handleRowClick(route)}
                                onMouseEnter={() => setHoveredRow(route.id)}
                                onMouseLeave={() => setHoveredRow(null)}
                            >   
                                    <td>{route.id}</td>
                                    <td>{route.routeStationTimeDTO.map(stationTime => stationTime.stationDTO.name).join(" → ")}</td>
                                    <td>{route.train.train}</td>
                                    <td>
                                        {route.routeStationTimeDTO[0] ? 
                                            new Date(route.routeStationTimeDTO[0].departureDate).toLocaleString() : '-'}
                                    </td>
                                    <td>
                                        {route.routeStationTimeDTO[route.routeStationTimeDTO.length - 1] ? 
                                            new Date(route.routeStationTimeDTO[route.routeStationTimeDTO.length - 1].arrivalDate).toLocaleString() : '-'}
                                    </td>
                                    <td style={{ position: "relative", textAlign: "center", width: "50px" }}> 
                                        <span 
                                            className="delete-icon"
                                            onClick={(e) => {
                                                e.stopPropagation(); 
                                                handleDeleteRoute(route.id);
                                            }}
                                            style={{
                                                cursor: "pointer",
                                                color: "black",
                                                fontSize: "14px",
                                                visibility: hoveredRow === route.id ? "visible" : "hidden" 
                                            }}
                                        >
                                            <FaTrash />
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div className={style.rightPart}>
                        {isAddVisible && (
                            <div className={style.addToute}>
                                <div className={commonStyle.firstRowAdd}>
                                    <h1 className={style.route}>Добавить маршрут</h1>
                                    <p onClick={toggleAddVisibility}>x</p>
                                </div>
                                <div className={style.contentAddRow}>
                                    <div className={style.partOfContent}>
                                        {stations.map((station, index) => (
                                            <div key={index} className={commonStyle.chooseLine}>
                                                <div className={style.param}>
                                                    <div key={index} className="chooseLine">
                                                        <div className={commonStyle.param}>
                                                            <h3>Станция</h3>
                                                            <input 
                                                                type="text"
                                                                list={`stationsList-${index}`}
                                                                placeholder="Введите станцию"
                                                                value={station.station}
                                                                onChange={(e) => {
                                                                    const newStations = [...stations];
                                                                    newStations[index].station = e.target.value;
                                                                    setStations(newStations);
                                                                }}
                                                            />
                                                            <datalist id={`stationsList-${index}`}>
                                                                {allStations.map((stationObj) => (
                                                                    <option key={stationObj.id} value={stationObj.name} />
                                                                ))}
                                                            </datalist>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className={commonStyle.param}>
                                                    <h3>Дата отправления</h3>
                                                    <input 
                                                        type="datetime-local" 
                                                        value={station.departureDate}
                                                        onChange={(e) => {
                                                            const newStations = [...stations];
                                                            newStations[index].departureDate = e.target.value;
                                                            setStations(newStations);
                                                        }}
                                                    />
                                                </div>
                                                <div className={commonStyle.param}>
                                                    <h3>Дата прибытия</h3>
                                                    <input 
                                                        type="datetime-local" 
                                                        value={station.arrivalDate}
                                                        onChange={(e) => {
                                                            const newStations = [...stations];
                                                            newStations[index].arrivalDate = e.target.value;
                                                            setStations(newStations);
                                                        }}
                                                    />
                                                </div>
                                                <FaTrash 
                                                    className={style.deleteIcon} 
                                                    onClick={() => removeStation(index)} 
                                                    style={{ cursor: "pointer", color: "black", fontSize: "1.0em" }} 
                                                />
                                            </div>
                                        ))}
                                        <button 
                                            className={commonStyle.buttonMainPage}
                                            onClick={addStation}>
                                            Добавить следующую станцию
                                        </button>   
                                    </div>
                                    <div className={style.partOfContent}>
                                        <div className={commonStyle.param}>
                                            <h3>Поезд</h3>
                                            <input 
                                                type="text"
                                                list="trainsList"
                                                placeholder="Выберите поезд"
                                                value={selectedTrain}
                                                onChange={(e) => {
                                                    const selected = allTrains.find(train => train.train === e.target.value);
                                                    if (selected) setSelectedTrain(selected.id);
                                                }}
                                            />
                                            <datalist id="trainsList">
                                                {allTrains.map((train) => (
                                                    <option key={train.id} value={train.train} />
                                                ))}
                                            </datalist>
                                        </div>
                                        <button 
                                            className={commonStyle.buttonMainPage} 
                                            onClick={selectedRoute ? handleUpdateRoute : handleSaveRoute}
                                        >
                                            {selectedRoute ? "Обновить маршрут" : "Сохранить маршрут"}
                                        </button>
                                    </div>
                                </div>
                            </div>
                        )}
                        {isStationVisible && (
                            <div className={style.stationForm}>
                                <p onClick={toggleStationVisibility}>x</p>
                                <div className={style.stationContent}>
                                    <div className={style.addStation}>
                                        <div><h1 className={style.route}>Добавить станцию</h1></div>
                                        <div className={commonStyle.param}>
                                            <h3>Введите название станции</h3>
                                            <input 
                                                type="text" 
                                                placeholder="Введите станцию"
                                                value={newStationName}
                                                onChange={(e) => setNewStationName(e.target.value)}
                                            />
                                            <button className={commonStyle.buttonMainPage} onClick={handleSaveStation}>
                                                Сохранить станцию
                                            </button>
                                        </div>
                                    </div>
                                    <div className={`${style.tableStation} ${commonStyle.tableRoute}`}>
                                        <thead className={commonStyle.theadRoute}>
                                            <tr className={commonStyle.first}>
                                                <th>ID</th>
                                                <th>Станция</th>
                                                <th></th>
                                            </tr>
                                        </thead>   
                                        <tbody>
                                            {allStations.map((station, index) => (
                                                <tr 
                                                key={index} 
                                                onClick={() => handleRowClick(station)}
                                                onMouseEnter={() => setHoveredRow(station.id)}
                                                onMouseLeave={() => setHoveredRow(null)}
                                                >    
                                                    <td>{station.id}</td>
                                                    <td>{station.name}</td>
                                                    <td style={{ position: "relative", textAlign: "center", width: "50px" }}> 
                                                        <span 
                                                            className="delete-icon"
                                                            onClick={(e) => {
                                                                e.stopPropagation(); 
                                                                handleDeleteTrain(station.id);
                                                            }}
                                                            style={{
                                                                cursor: "pointer",
                                                                color: "black",
                                                                fontSize: "14px",
                                                                visibility: hoveredRow === station.id ? "visible" : "hidden" 
                                                            }}
                                                        >
                                                        <FaTrash />
                                                        </span>
                                                    </td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default RoutePage;
