import style from "./usersBooking.module.css"; 
import userStyle from "../styles/userStyle.module.css";
import Header from "../../components/headerMain/headerMain";
import searchStyle from "../searchRoute/searchRoute.module.css";
import { useEffect, useState } from "react"; 
import axios from "axios"; 
import commonStyle from "../styles/forAllPAges.module.css";

const UsersBooking = () => {

    const [bookings, setBookings] = useState([]);

    const fetchBookings = async () => {
        try {
            const token = localStorage.getItem("accessToken"); 
            const response = await axios.get("http://localhost:8080/train/getBooking", {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            });
            setBookings(response.data); 
        } catch (error) {
            console.error("Ошибка загрузки бронирований:", error); 
        }
    };

    useEffect(() => {
        fetchBookings();
    }, []);

    const formatDateTime = (dateTime) => {
        if (!dateTime) return "-"; 
        return new Date(dateTime).toLocaleString("ru-RU", {
            day: "2-digit",
            month: "2-digit",
            year: "numeric",
            hour: "2-digit",
            minute: "2-digit",
        });
    };

    const cancelBooking = async (bookingId) => {
        if (!window.confirm("Вы уверены, что хотите отменить бронирование?")) return;

        try {
            const token = localStorage.getItem("accessToken");
            await axios.delete(`http://localhost:8080/train/cancelBooking/${bookingId}`, {
                headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
            });

            setBookings(bookings.filter((booking) => booking.bookingId !== bookingId));

            alert("Бронирование успешно отменено");
        } catch (error) {
            console.error("Ошибка отмены бронирования:", error);
            alert("Не удалось отменить бронирование");
        }
    };
    return ( 
        <div className={userStyle.page}>
            <Header/>
            <div className={userStyle.container}>   
                <div className={searchStyle.resultOfSearch}>
                    <div className={searchStyle.headerRoutes}>
                        <h1>Бронирование</h1>
                    </div>
                    <table className={`${searchStyle.tableRoutes} ${commonStyle.tableRoute}`}>        
                        <thead className={`${commonStyle.theadRoute} ${searchStyle.theadRoutes}`}>
                            <tr className={commonStyle.first}>
                                <th>Отправление</th>
                                <th>Прибытие</th>
                                <th>Поезд</th>
                                <th>Стоимость</th>
                                <th>Места</th>
                                <th></th>
                            </tr>
                        </thead>   
                        <tbody>
                        {bookings.map((booking) => (
                        <tr key={booking.bookingId}>
                            <td>
                                <div className={style.stationAndTime}>
                                    <div>{booking.departureStation.name}</div> 
                                    <div>{formatDateTime(booking.departureDataTime)}</div>
                                </div>
                            </td>
                            <td>                                
                                <div className={style.stationAndTime}>
                                    <div>{booking.arrivalStation.name}</div> 
                                    <div>{formatDateTime(booking.arrivalDataTime)}</div>
                                </div>
                            </td>
                            <td>{booking.train.train}</td>
                            <td>{booking.seatsList[0].price} BYN</td>
                            <td>{booking.seatsList.map((seat) => seat.number).join(", ")}</td>
                            <td>
                                <button 
                                    className={style.cancelBooking} 
                                    onClick={() => cancelBooking(booking.bookingId)}
                                >
                                    Отменить бронирование
                                </button>                            
                            </td>
                        </tr>
                    ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
 
export default UsersBooking;