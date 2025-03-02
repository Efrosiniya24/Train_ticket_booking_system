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
    
    const handleRowClick = (route) => {
        setSelectedTrain(route.train.id);
        setStations(route.routeStationTimeDTO.map((stationTime, index) => ({
            station: stationTime.stationDTO.name,
            departureDate: stationTime.departureDate,
            arrivalDate: stationTime.arrivalDate,
        })));
        setIsAddVisible(true); 
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
                            </tr>
                        </thead>   
                        <tbody>
                            {routes.map((route, index) => (
                                <tr key={index} onClick={() => handleRowClick(route)} style={{ cursor: "pointer" }}>
                                    <td>{index + 1}</td>
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
                                        <button className={commonStyle.buttonMainPage} onClick={handleSaveRoute}>
                                            Сохранить маршрут
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
                                            </tr>
                                        </thead>   
                                        <tbody>
                                            {allStations.map((station, index) => (
                                                <tr key={index} style={{ cursor: "pointer" }}>
                                                    <td>{station.id}</td>
                                                    <td>{station.name}</td>
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
