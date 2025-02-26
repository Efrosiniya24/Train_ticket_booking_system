import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import header from "./header.module.css";
import allStyle from "../../pages/main/styles/forAllPAges.module.css"

const HeaderMain = () => {
    return ( 
        <header className={header.headerNotMain}>
            <div className={header.nav}>
                <p className={header.logo}>Train</p>
                <ul>
                    <li>Билеты</li>
                    <li>Личный кабинет</li>
                </ul>
            </div>
        </header>
    );
}

export default HeaderMain;