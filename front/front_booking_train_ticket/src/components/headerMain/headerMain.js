import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import header from "./header.module.css";
import allStyle from "../../pages/styles/forAllPAges.module.css"

const HeaderMain = () => {
    return ( 
        <header className={header.headerNotMain}>
            <div className={header.nav}>
                <p className={header.logo}>Train</p>
                <ul>
                    <NavLink to="/searchRoute"><li>Билеты</li></NavLink>
                    <NavLink to="/usersBooking"><li>Личный кабинет</li></NavLink>
                </ul>
            </div>
        </header>
    );
}

export default HeaderMain;