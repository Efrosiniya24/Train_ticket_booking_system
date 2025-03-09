import React, { useState } from "react";
import style from "./seat.module.css";

const Seat = ({ seat }) => {
    const [isSelected, setIsSelected] = useState(false);

    const handleClick = () => {
        if (!seat.booked) { 
            setIsSelected(!isSelected);
        }
    };

    return (
        <div
            className={`${style.seat} 
                        ${seat.booked ? style.bookedSeat : style.freeSeat} 
                        ${isSelected ? style.selectedSeat : ""}`} 
            onClick={handleClick}
        >
            <span className={style.seatNumber}>{seat.seatId}</span>
        </div>
    );
};

export default Seat;