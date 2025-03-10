import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import style from "./sideBar.module.css";
import route from "./icons/route.png";
import train from "./icons/train.png"
import logout from "./icons/exit.png";
import axios from "axios"; 

const SideBarAdmin = () => {
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            const token = localStorage.getItem('accessToken');

            const response = await axios.post(
                'http://localhost:8080/train/auth/logout', 
                {}, 
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                }
            );

            if (response.status === 200) {
                localStorage.removeItem("accessToken");
                localStorage.removeItem("userId");
                localStorage.removeItem("userRole");
                sessionStorage.removeItem("accessToken");
                sessionStorage.removeItem("userId");
                sessionStorage.removeItem("userRole");
                navigate('/');
            } else {
                throw new Error('Logout failed');
            }
        } catch (error) {
            console.error('Logout error:', error);
        }
    };


    return ( 
        <aside className={style.sideBar}>
            <ul>
                <li>
                    <NavLink to="/route" activeClassName="active" className={style.menuLine}>
                        <img src={route} alt="Route icon" className={style.menuIcon}/>
                        <p className={style.menu}>Марштуры</p>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/train" activeClassName="active" className={style.menuLine}>
                        <img src={train} alt="Train icon" className={style.menuIcon}/>
                        <p className={style.menu}>Поезда</p>
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/" activeClassName="active" className={style.menuLine} onClick={handleLogout}>
                        <img src={logout} alt="Logout icon" className={style.menuIcon}/>
                        <p className={style.menu}>Выход</p>
                    </NavLink>
                </li>
            </ul>
    </aside>
    );
}
 
export default SideBarAdmin;