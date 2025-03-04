import Header from "../../components/headerMain/headerMain";
import userStyle from "../styles/userStyle.module.css";
import style from "./main.module.css";
import photo from "./train.png";
import { NavLink } from "react-router-dom"

const Main = () => {
    return (  
        <div className={userStyle.page}>
            <Header/>
            <div className={userStyle.container}>
                <div className={style.box}>
                    <div className={style.rightPart}>
                        <div className={style.text}>
                            <p>Train ticket</p>
                            <p>booking</p>
                            <p>system</p>
                        </div>
                        <div className={style.buttons}>
                            <NavLink to="/searchRoute">
                                <button className={style.buttonMainPage}>
                                    Найти билет
                                </button>
                            </NavLink>
                            <NavLink to="/signIn">
                                <button className={style.buttonMainPage}>
                                    Войти
                                </button>
                            </NavLink>
                        </div>
                    </div>
                    <div>
                        <img src={photo} alt="Train photo" className={style.photo}/>
                    </div>
                </div>
            </div>
        </div>
    );
}
 
export default Main;