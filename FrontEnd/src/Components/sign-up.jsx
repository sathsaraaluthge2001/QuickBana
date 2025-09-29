import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import '../css/style-signup.css';

export default function RegisterHandle() {
    const [txtEmail, setEmail] = useState("");
    const [txtPassword, setPassword] = useState("");
    const [txtCPassword, setCPassword] = useState("");
    const [txtName, setName] = useState("");

    function sendData(e) {
        e.preventDefault();

        if (!txtEmail || !txtPassword || !txtCPassword || !txtName) {
            alert("Please fill in all fields");
            return;
        }

        if (txtPassword !== txtCPassword) {
            alert("Passwords do not match!");
            return;
        }

        axios.post("http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/users/", 
            {
                name: txtName,
                email: txtEmail,
                password: txtPassword
            }, 
            {
                headers: {
                    "Content-Type": "application/json",
                },
                withCredentials: true
            }
        )
        .then((res) => {
            console.log("Registration successful:", res.data);
            if (res.data.name === txtName && res.data.email === txtEmail) {
                alert("Successfully registered");
                window.location.href = `/`;
            } else {
                alert("Registration failed");
            }
        })
        .catch((err) => {
            console.error("Registration failed", err);
            alert("Registration failed: " + (err.response?.data?.message || err.message));
        });
    }

    return (
        <div>
            <div className="container">
                <h2>Create Your Account</h2>
                <form onSubmit={sendData}>
                    <input type="text" placeholder="Username" required onChange={(e) => setName(e.target.value)} />
                    <input type="email" placeholder="Email" required onChange={(e) => setEmail(e.target.value)} />
                    <input type="password" placeholder="Password" required onChange={(e) => setPassword(e.target.value)} />
                    <input type="password" placeholder="Confirm Password" required onChange={(e) => setCPassword(e.target.value)} />
                    <button type="submit">Sign Up</button>
                </form>
                <p>Already have an account? <Link to="/">Sign In</Link></p>
            </div>
        </div>
    );
}
