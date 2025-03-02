import style from "./add.module.css"

const Plus = ({onClick}) => {
    return ( 
        <div className={style.plus} onClick={onClick}>
            <p>+</p>
        </div>
    );
}
 
export default Plus;