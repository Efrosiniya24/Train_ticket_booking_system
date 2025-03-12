import { useEffect, useState } from "react";  
import axios from "axios"; 
import SideBarAdmin from "../../components/sideBar/sideBar";  
import style from "./train.module.css";
import commonStyle from "../styles/forAllPAges.module.css";
import { FaTrash } from "react-icons/fa"; 

const Train = () => {
    const [isAddVisible, setIsAddVisible] = useState(false);
    const [trains, setTrains] = useState([]);
    const [selectedTrain, setSelectedTrain] = useState("");
    const [trainName, setTrainName] = useState("");
    const [numberOfCoaches, setNumberOfCoaches] = useState("");
    const [seatsPerCoach, setSeatsPerCoach] = useState([]);
    const [seatPrice, setSeatPrice] = useState("");
    const [hoveredRow, setHoveredRow] = useState(null);

    useEffect(() => {
        fetchTrains();
    }, []);

    const fetchTrains = async () => {
        try {
            const token = localStorage.getItem("accessToken");
            const response = await axios.get("http://localhost:8080/train/allTrains", {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            });
            setTrains(response.data);
        } catch (error) {
            console.error("Ошибка загрузки поездов:", error);
        }
    };

    const toggleAddVisibility = () => {
        setIsAddVisible(!isAddVisible);
    };

    const handleRowClick = (train) => {
        setSelectedTrain(train.id);
        setIsAddVisible(true);
        
        setTrainName(train.train);
        setNumberOfCoaches(train.numberOfCoaches);
        const seatsArray = train.coachDtoList.map(coach => coach.numberOfSeats.toString());
        setSeatsPerCoach(seatsArray);
        if (train.numberOfCoaches > 0 && train.coachDtoList[0].seats.length > 0) {
            setSeatPrice(train.coachDtoList[0].seats[0].price.toString());
        } else {
            setSeatPrice("");
        }
    };    
    
    const handleTrainNameChange = (event) => {
        setTrainName(event.target.value);
    };

    const handleNumberOfCoachesChange = (event) => {
        const value = event.target.value;
        setNumberOfCoaches(value);

        if (value > 0) {
            setSeatsPerCoach(new Array(parseInt(value)).fill(""));
        } else {
            setSeatsPerCoach([]);
        }
    };

    const handleSeatsChange = (index, event) => {
        const newSeats = [...seatsPerCoach];
        newSeats[index] = event.target.value;
        setSeatsPerCoach(newSeats);
    };

    const handleSeatPriceChange = (event) => {
        setSeatPrice(event.target.value);
    };
    
    const handleSaveTrain = async () => {
        if (!trainName || numberOfCoaches <= 0 || !seatPrice) {
            alert("Введите название поезда, количество вагонов и цену за место!");
            return;
        }
    
        const trainDto = {
            train: trainName,
            coachDtoList: seatsPerCoach.map((seats, index) => ({
                number: index + 1,
                numberOfSeats: parseInt(seats) || 0,
                seats: new Array(parseInt(seats) || 0).fill(null).map((_, seatIndex) => ({
                    number: seatIndex + 1,
                    price: parseFloat(seatPrice) || 0 
                }))
            })),
            numberOfCoaches: numberOfCoaches
        };
    
        try {
            const token = localStorage.getItem("accessToken");
            await axios.post("http://localhost:8080/train/addTrain", trainDto, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            });
    
            alert("Поезд успешно добавлен!");
            fetchTrains();
            setIsAddVisible(false);
            setTrainName("");
            setNumberOfCoaches("");
            setSeatsPerCoach([]);
            setSeatPrice("");
    
        } catch (error) {
            console.error("Ошибка при сохранении поезда:", error);
            alert("Ошибка при сохранении поезда!");
        }
    };

    const handleDeleteTrain = async (trainId) => {
        if (!window.confirm("Вы уверены, что хотите удалить этот поезд?")) {
            return;
        }

        try {
            const token = localStorage.getItem("accessToken");
            await axios.delete(`http://localhost:8080/train/delete/${trainId}`, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            }); 

            setTrains(trains.filter(train => train.id !== trainId));
        } catch (error) {
            console.error("Ошибка при удалении поезда:", error);
            alert("Ошибка при удалении поезда!");
        }
    };

    const handleUpdateTrain = async () => {
        if (!trainName || numberOfCoaches <= 0 || !seatPrice) {
            alert("Введите название поезда, количество вагонов и цену за место!");
            return;
        }
    
        const trainDto = {
            id: selectedTrain,
            train: trainName,
            coachDtoList: seatsPerCoach.map((seats, index) => ({
                id: selectedTrain ? trains.find(t => t.id === selectedTrain)?.coachDtoList[index]?.id || null : null,
                number: index + 1,
                numberOfSeats: parseInt(seats) || 0,
                seats: new Array(parseInt(seats) || 0).fill(null).map((_, seatIndex) => ({
                    id: selectedTrain ? trains.find(t => t.id === selectedTrain)?.coachDtoList[index]?.seats[seatIndex]?.id || null : null,
                    number: seatIndex + 1,
                    price: parseFloat(seatPrice) || 0 
                }))
            })),
            numberOfCoaches: numberOfCoaches
        };
    
        try {
            const token = localStorage.getItem("accessToken");
            await axios.put(`http://localhost:8080/train/update/${selectedTrain}`, trainDto, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            });
    
            alert("Поезд успешно обновлен!");
            fetchTrains();
            setIsAddVisible(false);
            setSelectedTrain("");
            setTrainName("");
            setNumberOfCoaches("");
            setSeatsPerCoach([]);
            setSeatPrice("");
    
        } catch (error) {
            console.error("Ошибка при обновлении поезда:", error);
            alert("Ошибка при обновлении поезда!");
        }
    }; 

    const handleSaveOrUpdateTrain = () => {
        if (selectedTrain) {
            handleUpdateTrain();
        } else {
            handleSaveTrain();
        }
    };
    

    return ( 
        <div>
            <SideBarAdmin/>
            <div className={commonStyle.containerAdmin}>
                <div className={commonStyle.row}>
                    <h1 className={style.trains}>Поезда</h1>
                </div>
                <div className={commonStyle.secondPart}>
                    <table className={`${style.tableTrain} ${commonStyle.tableRoute}`}>        
                        <thead className={commonStyle.theadRoute}>
                            <tr className={commonStyle.first}>
                                <th>ID</th>
                                <th>Поезд</th>
                                <th></th>
                            </tr>
                        </thead>   
                        <tbody>
                            {trains.map((train, index) => (
                                <tr 
                                    key={index} 
                                    onClick={() => handleRowClick(train)}
                                    onMouseEnter={() => setHoveredRow(train.id)}
                                    onMouseLeave={() => setHoveredRow(null)}
                                >                                    
                                    <td>{train.id}</td>
                                    <td>{train.train}</td>
                                    <td style={{ position: "relative", textAlign: "center", width: "50px" }}> 
                                        <span 
                                            className="delete-icon"
                                            onClick={(e) => {
                                                e.stopPropagation(); 
                                                handleDeleteTrain(train.id);
                                            }}
                                            style={{
                                                cursor: "pointer",
                                                color: "black",
                                                fontSize: "14px",
                                                visibility: hoveredRow === train.id ? "visible" : "hidden" 
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
                        <div className={style.addTrain}>
                            <div className={commonStyle.firstRowAdd}>
                                <h1 className={style.route}>Добавить поезд</h1>
                                <p onClick={toggleAddVisibility}>x</p>
                            </div>
                            <div className={style.contentAddRow}>
                                <div className={style.partOfContent}>
                                    <div className={commonStyle.chooseLine}>
                                        <div className={`${style.param} ${commonStyle.param}`}>
                                            <h3>Введите название поезда</h3>
                                            <input 
                                                type="text"
                                                placeholder="Введите название поезда"
                                                value={trainName}
                                                onChange={handleTrainNameChange}
                                            />
                                        </div>
                                        <div className={`${style.param} ${commonStyle.param}`}>
                                            <h3>Введите количество вагонов</h3>
                                            <input 
                                                type="number"
                                                placeholder="Введите количество вагонов"
                                                min="1"
                                                value={numberOfCoaches}
                                                onChange={handleNumberOfCoachesChange}
                                            />
                                        </div>
                                        
                                    </div>
                                    <div className={commonStyle.param} style={{ gap: "30px" }}>
                                        {trainName && numberOfCoaches > 0 &&
                                            seatsPerCoach.map((_, index) => (
                                                <div key={index} className={`${style.param} ${commonStyle.param}`}>
                                                    <h3>Количество мест в вагоне {index + 1}</h3>
                                                    <input 
                                                        type="number"
                                                        placeholder="Введите количество мест"
                                                        min="1"
                                                        value={seatsPerCoach[index]}
                                                        onChange={(event) => handleSeatsChange(index, event)}
                                                    />
                                                </div>
                                            ))
                                        }
                                        {trainName && numberOfCoaches > 0 && (
                                            <>
                                                <div className={`${style.param} ${commonStyle.param}`}>
                                                    <h3>Введите стоимость места</h3>
                                                    <input 
                                                        type="number"
                                                        placeholder="Введите стоимость места"
                                                        min="1"
                                                        value={seatPrice}  
                                                        onChange={handleSeatPriceChange} 
                                                    />
                                                </div>
                                                <button 
                                                    className={`${style.buttonMainPage} ${commonStyle.buttonMainPage}`} 
                                                    onClick={handleSaveOrUpdateTrain}
                                                >
                                                    {selectedTrain ? "Обновить поезд" : "Сохранить поезд"}
                                                </button>
                                            </>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
 
export default Train;