    import React, { useState } from "react";
    import style from "./seat.module.css";

    const Seat = ({ seat, isSelected, onSelect }) => {
        return (
            <div
                className={`${style.seat} 
                            ${seat.booked ? style.bookedSeat : style.freeSeat} 
                            ${isSelected ? style.selectedSeat : ""}`} 
                onClick={onSelect}
            >
                <span className={style.seatNumber}>{seat.seatId}</span>
            </div>
        );
    };

    export default Seat;