import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/style-signin.css";
import { Link } from "react-router-dom";

export default function LoginHandle() {
    const [txtEmail, setEmail] = useState("");
    const [txtPassword, setPassword] = useState("");
    const navigate = useNavigate();

    function sendData(e) {
        e.preventDefault();

        if (!txtEmail || !txtPassword) {
            alert("Please fill in all fields");
            return;
        }

        axios.post(
            "http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/users/login",
            { email: txtEmail, password: txtPassword },
            { headers: { "Content-Type": "application/json" }, withCredentials: true }
        )
        .then((res) => {
            console.log("Login successful:", res.data);
            if (res.data.message === "Login successful") { 
                localStorage.setItem("user", JSON.stringify(res.data.userDTO));
                localStorage.setItem("token", res.data.token);
                alert(res.data.message);
                navigate("/home");
            } else {
                alert(res.data.message);
            }
        })
        .catch((err) => {
            console.error("Login failed", err);
            alert("Login failed: " + err.message);
        });
    }

    return (
        <div className="container">
            <h2>Welcome Back!</h2>
            <form onSubmit={sendData}>
                <input 
                    type="email" 
                    placeholder="Email" 
                    required 
                    onChange={(e) => setEmail(e.target.value)} 
                />
                <input 
                    type="password" 
                    placeholder="Password" 
                    required 
                    onChange={(e) => setPassword(e.target.value)} 
                />
                <button type="submit">Sign In</button>
            </form>
            <p>Don't have an account? <Link to="/sign-up">Sign Up</Link></p>
        </div>
    );
}
