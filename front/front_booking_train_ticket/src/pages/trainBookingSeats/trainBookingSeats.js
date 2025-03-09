import style from "./trainBookingSeats.module.css"; 
import userStyle from "../styles/userStyle.module.css";
import Header from "../../components/headerMain/headerMain";
import { useEffect, useState } from "react"; 
import { useLocation } from "react-router-dom";
import Seat from "../../components/seat/seat";
import axios from "axios"; 

const TrainBookingSeats = () => {
    const location = useLocation();
    const routeData = location.state?.routeData || {};
    const [coachList, setCoachList] = useState([]);
    const [selectedCoach, setSelectedCoach] = useState(null);
    const [selectedSeats, setSelectedSeats] = useState({});
    const departureStation = location.state?.departureStation || {};
    const arrivalStation = location.state?.arrivalStation || {};

    useEffect(() => {
        if (routeData) {
            const coaches = Object.values(routeData.coachDtos || {});
            setCoachList(coaches);
    
            if (coaches.length > 0) {
                console.log("Первый вагон:", coaches[0]);
                setSelectedCoach(coaches[0]);
            }
        }
    }, [routeData]);

    const handleCoachClick = (coach) => {
        setSelectedCoach(coach);
    };

    const handleSeatSelect = (coachId, seatId) => {

        setSelectedSeats((prevSeats) => {
            const updatedSeats = { ...prevSeats };
    
            if (!updatedSeats[coachId]) {
                updatedSeats[coachId] = [];
            }
    
            if (updatedSeats[coachId].includes(seatId)) {
                updatedSeats[coachId] = updatedSeats[coachId].filter((id) => id !== seatId);
            } else {
                updatedSeats[coachId] = [...updatedSeats[coachId], seatId];
            }
            return updatedSeats;
        });
    };

    const handleBooking = async () => {
        const token = localStorage.getItem("accessToken");
        const seatsList = Object.entries(selectedSeats).flatMap(([coachId, seats]) =>
            seats.map((seatId) => {
                const coach = coachList.find(c => c.coachId == coachId);
                const seatIndex = coach?.seats.findIndex(s => s.seatId === seatId); 
                
                return {
                    id: seatId,
                    number: seatIndex !== -1 ? seatIndex + 1 : null,
                    price: routeData. price
                };
            })
        );

        const bookingData = {
            seatsList,
            trainId: routeData.trainDTO.id, 
            routeId: routeData.routeDTO.id, 
            departureStation: routeData.routeDTO.routeStationTimeDTO[0].stationDTO,
            arrivalStation: routeData.routeDTO.routeStationTimeDTO[1].stationDTO,
            travelDate: routeData.departureDateTime 
        };
    
        try {
            const response = await axios.post('http://localhost:8080/train/booking', bookingData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                }
            });
            alert("Бронирование прошло успешно!"); 
        } catch (error) {
            alert("Ошибка при бронировании. Попробуйте еще раз."); 
        }
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
                                <div className= {style.rowOverCoach}>
                                    <p><b>Свободные места: </b>{selectedCoach.freeSeats}</p>
                                    <p><b>Стоимость: </b>{routeData. price} BYN</p>
                                </div>
                                <div className={style.coach}>   
                                    {selectedCoach.seats.map((seat) => (
                                        <Seat 
                                            key={seat.seatId} 
                                            seat={seat} 
                                            isSelected={selectedSeats[selectedCoach.coachId]?.includes(seat.seatId)} 
                                            onSelect={() => handleSeatSelect(selectedCoach.coachId, seat.seatId)} 
                                        />
                                    ))}
                                </div>
                            </div>
                        ) : (
                            <p>Выберите вагон, чтобы увидеть список мест</p>
                        )}
                        <div className={style.order}>
                            <h2>Заказ</h2>
                            {Object.keys(selectedSeats).length > 0 ? (
                                Object.entries(selectedSeats).map(([coachId, seats]) => {
                                    const foundCoach = coachList.find(c => c.coachId == coachId); 
                                    const coachIndex = foundCoach ? coachList.indexOf(foundCoach) + 1 : "не найден";

                                    return foundCoach && seats.length > 0 ? (
                                        <p key={coachId}>
                                            Вагон {coachIndex}: место {seats.sort((a, b) => a - b).join(", ")}
                                        </p>
                                    ) : null;
                                })
                            ) : (
                                <p>Выберите места</p>
                            )}
                            <button className={style.book} onClick={handleBooking}>                                
                                <p>Забронировать</p>
                            </button> 
                        </div>
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
