import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import style from "./sideBar.module.css";
import route from "./icons/route.png";


const SideBarAdmin = () => {
    return ( 
        <aside className={style.sideBar}>
            <ul>
                <li>
                    <NavLink to="/route" activeClassName="active" className={style.menuLine}>
                        <img src={route} alt="Route icon" className={style.menuIcon}/>
                        <p className={style.menu}>Марштуры</p>
                    </NavLink>
                </li>
            </ul>
    </aside>
    );
}
 
export default SideBarAdmin;