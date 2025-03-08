import style from "./searchRoute.module.css";
import userStyle from "../styles/userStyle.module.css";
import commonStyle from "../styles/forAllPAges.module.css";
import Header from "../../components/headerMain/headerMain";
import { useEffect, useState } from "react"; 
import axios from "axios"; 

const SearchRoute = () => {

    const [stations, setStations] = useState([{ departureStation: "", arrivalStation: "", departureDate: "" }]);
    const [allStations, setAllStations] = useState([]);
    const [isLoading, setIsLoading] = useState(false); 
    const [error, setError] = useState(null);
    const [searchResults, setSearchResults] = useState();
        const [hoveredRow, setHoveredRow] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);
            setError(null);
            try {
                const token = localStorage.getItem("accessToken");
    
                const results = await Promise.allSettled([
                    axios.get("http://localhost:8080/station/allStations", {
                        headers: { Authorization: `Bearer ${token}` },
                    }),
                ]);

                if (results[0].status === "fulfilled") {
                    setAllStations(results[0].value.data);
                } else {
                    console.error("Ошибка загрузки станций:", results[1].reason);
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

    const handleSearch = async () => {
        if (
            stations.length === 0 || 
            stations[0].departureStation.trim() === "" || 
            stations[0].arrivalStation.trim() === "" || 
            stations[0].departureDate.trim() === ""
        ) {
            alert("Введите станцию отправления, прибытия и дату!");
            return;
        }
        
        const departureStationObj = allStations.find(station => station.name === stations[0].departureStation);
        const arrivalStationObj = allStations.find(station => station.name === stations[0].arrivalStation); 
    
        const searchTicketDTO = {
            departureCityId: departureStationObj.id,
            arrivalCityId: arrivalStationObj.id,
            departureDate: stations[0].departureDate
        };
    
        try {
            const token = localStorage.getItem("accessToken");
            const response = await axios.post("http://localhost:8080/route/searchRoutes", searchTicketDTO, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });
            if (response.data && response.data.length > 0) {
                const routes = response.data;
                setSearchResults(routes);

                const seatRequests = routes.map(async (route) => {
                    const seatStatusDTO = {
                        trainId: route.trainDTO.id,               
                        routeId: route.id,                        
                        departureStationId: route.departureCityId, 
                        arrivalStationId: route.arrivalCityId      
                    };
    
                    const seatResponse = await axios.post("http://localhost:8080/train/seats/seatWithStatus",seatStatusDTO, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                        },
                        data: seatStatusDTO, 
                    });
    
                    return {
                        routeId: route.id,
                        freeSeats: seatResponse.data.freeSeats,
                    };
                });
    
                const seatStatuses = await Promise.all(seatRequests);
    
                const updatedRoutes = routes.map(route => {
                    const seatData = seatStatuses.find(s => s.routeId === route.id);
                    return { ...route, freeSeats: seatData ? seatData.freeSeats : 0 };
                });
    
                setSearchResults(updatedRoutes);
            } else {
                setSearchResults(); 
            }
            console.log("Найденные маршруты:", response.data);
        } catch (error) {
            console.error("Ошибка поиска маршрута:", error);
        }
    };
    

    return ( 
        <div>
            <div className={userStyle.page}>
                <Header/>
                <div className={userStyle.container}>
                    {stations.map((station, index) => (
                        <div className={style.box}>
                            <div key={index} className={style.chooseLine}>
                                <div className={commonStyle.param}>
                                    <h3>Откуда</h3>
                                    <input 
                                        type="text"
                                        list={`stationsList-${index}`}
                                        placeholder="Введите станцию"
                                        value={stations[0].departureStation}
                                        onChange={(e) => {
                                            const newStations = [...stations];
                                            newStations[0].departureStation = e.target.value;
                                            setStations(newStations);
                                        }}
                                    />
                                    <datalist id={`stationsList-${index}`}>
                                        {allStations.map((stationObj) => (
                                            <option key={stationObj.id} value={stationObj.name} />
                                        ))}
                                    </datalist>
                                </div>

                                <div className={commonStyle.param}>
                                    <h3>Куда</h3>
                                    <input 
                                        type="text"
                                        list={`stationsList-${index}`}
                                        placeholder="Введите станцию"
                                        value={stations[0].arrivalStation}
                                        onChange={(e) => {
                                            const newStations = [...stations];
                                            newStations[0].arrivalStation = e.target.value;
                                            setStations(newStations);
                                        }}
                                    />
                                    <datalist id={`stationsList-${index}`}>
                                        {allStations.map((stationObj) => (
                                            <option key={stationObj.id} value={stationObj.name} />
                                        ))}
                                    </datalist>
                                </div>
                                <div className={commonStyle.param}>
                                    <h3>Когда</h3>
                                    <input 
                                        type="date" 
                                        placeholder="Укажите день"
                                        value={station.departureDate}
                                        onChange={(e) => {
                                            const newStations = [...stations];
                                            newStations[index].departureDate = e.target.value;
                                            setStations(newStations);
                                        }}
                                    />
                                </div>
                                    <button className={style.buttonSearch} onClick={handleSearch}>Найти</button>
                                </div>
                        </div>
                    ))}

                    {searchResults && (
                        <div className={style.resultOfSearch}>
                            <div className={style.headerRoutes}>
                                <h1>
                                    {`${searchResults?.[0]?.departureCity || "Нет данных"} – 
                                    ${searchResults?.[0]?.arrivalCity || "Нет данных"}, 
                                    ${new Date(searchResults?.[0]?.departureDateTime).toLocaleDateString("ru-RU", { day: "numeric", month: "long" }) || "Нет данных"}`}
                                </h1>
                            </div>
                            <table className={`${style.tableRoutes} ${commonStyle.tableRoute}`}>        
                                <thead className={`${commonStyle.theadRoute} ${style.theadRoutes}`}>
                                    <tr className={commonStyle.first}>
                                        <th>Поезд</th>
                                        <th>Прибытие</th>
                                        <th>Отбытие</th>
                                        <th>Продолжительность</th>
                                        <th>Стоимость</th>
                                        <th>Свободные места</th>
                                    </tr>
                                </thead>   
                                <tbody>
                                    {searchResults.map((route) => (                                       
                                        <tr 
                                            key={route.id} 
                                            onMouseEnter={() => setHoveredRow(route.id)}
                                            onMouseLeave={() => setHoveredRow(null)}
                                        >                                  
                                            <td>{`${route.trainDTO.train}
                                            ${route.firstCityRoute} - ${route.lastCityRoute}`}</td>
                                            <td>{new Date(searchResults?.[0]?.arrivalDateTime)
                                                .toLocaleTimeString("ru-RU", { hour: "2-digit", minute: "2-digit" }) || "Нет данных"}
                                            </td>
                                            <td>{new Date(searchResults?.[0]?.departureDateTime)
                                                .toLocaleTimeString("ru-RU", { hour: "2-digit", minute: "2-digit" }) || "Нет данных"}
                                            </td>
                                            <td>{searchResults?.[0]?.timeRoad || "Нет данных"}</td>
                                            <td>{route.price} BYN</td>
                                            <td>{route.freeSeats}
                                                {route.freeSeats > 0 && <button className={style.chooseSeat}>Выбрать место</button>}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>

                        </div>
                    )}


                </div>
            </div>
        </div>
    );
}

export default SearchRoute;