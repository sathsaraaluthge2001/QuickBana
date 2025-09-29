import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../css/style-home.css";


export default function Home() {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Retrieve user data from localStorage
        const storedUser = localStorage.getItem("user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        } else {
            // Redirect to login if not logged in
            navigate("/");
        }
    }, [navigate]);

    function handleLogout() {
        localStorage.removeItem("user");
        localStorage.removeItem("token");
        navigate("/");
    }

    return (
        <div className="container">
            {user ? (
                <>
                    {/*<h2>Welcome, {user.email}!</h2> */}
                    <h2>Play & Win</h2>
                    <button><Link to="/levels">Play Game</Link></button>
                    <button><Link to="/leader-board">Scoreboard</Link></button>
                    <button><Link to="/profile">Profile</Link></button>
                    <button onClick={handleLogout}>Logout</button>
                </>
            ) : (
                <h2>Loading...</h2>
            )}
        </div>
    );
}
