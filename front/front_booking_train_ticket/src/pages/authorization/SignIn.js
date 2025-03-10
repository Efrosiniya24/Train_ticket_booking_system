import React, { useState } from "react"; 
import { useNavigate, NavLink, useLocation } from "react-router-dom";
import axiosInstance from "./axiosInstance";
import login from "./login.module.css";

const SignIn = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const location = useLocation();

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
//       localStorage.removeItem("accessToken");
// sessionStorage.removeItem("accessToken");

      const response = await axiosInstance.post("/train/auth/signIn", { email, password });

      console.log("Server Response:", response.data);

      if (response.status !== 200 || !response.data.userId) {
        setErrorMessage("Ошибка: сервер не вернул идентификатор пользователя");
        return;
      }

      const { userId, role, accessToken, refreshToken } = response.data;

      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);
      localStorage.setItem("userRole", role);
      localStorage.setItem("userId", userId);

      console.log("User ID saved in localStorage:", userId);

      const redirectPath = location.state?.from?.pathname || (role === "USER" ? "/" : "/route");

      switch (role) {
        case "USER":
          navigate("/searchRoute");
          break;
        case "ADMIN":
          navigate("/route");
          break;
        default:
          setErrorMessage("Неизвестная роль пользователя");
      }
    } catch (error) {
      if (error.response) {
        console.log(error.response.status);
        if (error.response.status === 403) {
          setErrorMessage("Ваш аккаунт деактивирован. Пожалуйста, свяжитесь с поддержкой.");
        } else {
          setErrorMessage("Неверный логин или пароль");
        }
      } else {
        console.error("Ошибка сети или сервера:", error);
        setErrorMessage("Проблема с подключением к серверу.");
      }
    }
  };

  return (
    <div className={login.mainLogin}>
      <div className={login.containerLogin}>
        <div className={login.sign_in}>
          <div className={login.form_container}>
            <form onSubmit={handleSubmit}>
              <h1>Войти</h1>
              <input
                type="email"
                name="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <input
                type="password"
                name="password"
                placeholder="Пароль"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <button type="submit">Войти</button>
            </form>
            {errorMessage && <p className={login.error}>{errorMessage}</p>}
          </div>
        </div>
        <div className={login.toggle_container}>
          <div className={login.toggle}>
            <div className={login.toggle_panel}>
              <div className={login.toggle_right}>
                <h1>
                  Добро <br />
                  пожаловать!
                </h1>
                <p>Введите Ваши персональные данные для авторизации</p>
                <NavLink to="/signUp">
                  <button className={login.hidden}>Зарегистрироваться</button>
                </NavLink>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignIn;
