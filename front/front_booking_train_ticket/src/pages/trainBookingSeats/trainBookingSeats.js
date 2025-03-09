import style from "./trainBookingSeats.module.css"; 
import userStyle from "../styles/userStyle.module.css";
import Header from "../../components/headerMain/headerMain";
import { useEffect, useState } from "react"; 
import { useLocation } from "react-router-dom";
import Seat from "../../components/seat/seat"

const TrainBookingSeats = () => {
    const location = useLocation();
    const routeData = location.state?.routeData || {};
    const [coachList, setCoachList] = useState([]);
    const [selectedCoach, setSelectedCoach] = useState(null); 

    useEffect(() => {
        if (routeData) {
            const coaches = Object.values(routeData.coachDtos || {});
            console.log("Полученные данные от сервера (до установки в стейт):", routeData.coachDtos);
    
            setCoachList(coaches);
            console.log("Данные после установки в state:", coaches);
    
            if (coaches.length > 0) {
                setSelectedCoach(coaches[0]);
            }
        }
    }, [routeData]);
    
    

    const handleCoachClick = (coach) => {
        setSelectedCoach(coach);
    };

    return ( 
        <div>
            <div className={userStyle.page}>
                
                <Header/>
                <div className={`${userStyle.container} ${style.container}`}>
                    <div className={style.train}>
                        {selectedCoach ? (
                            <div>
                                <h2>Вагон {coachList.indexOf(selectedCoach) + 1}</h2>
                                <br/>
                                <p><b>Свободные места: </b>{selectedCoach.freeSeats}</p>

                                <div className={style.coach}>   
                                    {selectedCoach.seats.map((seat) => (
                                        <Seat key={seat.seatId} seat={seat} /> 
                                    ))}
                                </div>
                            </div>
                        ) : (
                            <p>Выберите вагон, чтобы увидеть список мест</p>
                        )}
                    </div>

                    <div className={style.coachesButtons}>
                        {coachList.map((coach, index) => (
                            <button key={index} onClick={() => handleCoachClick(coach)}>
                                Вагон {index + 1}
                            </button> 
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TrainBookingSeats;
