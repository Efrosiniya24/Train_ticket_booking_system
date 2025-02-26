import Header from "../../components/headerMain/headerMain";
import style from "./main.module.css";
import photo from "./train.png";
import { NavLink } from "react-router-dom"

const Main = () => {
    return (  
        <div className={style.mainPage}>
            <Header/>
            <div className={style.container}>
                <div className={style.box}>
                    <div className={style.rightPart}>
                        <div className={style.text}>
                            <p>Train ticket</p>
                            <p>booking</p>
                            <p>system</p>
                        </div>
                        <div className={style.buttons}>
                            <button className={style.buttonMainPage}>
                                Найти билет
                            </button>
                            <button className={style.buttonMainPage}>
                                Войти
                            </button>
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