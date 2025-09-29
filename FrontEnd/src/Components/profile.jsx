import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/style-profile.css"; // Ensure this file exists

export default function Profile1Handle() {
  const [name, setName] = useState(""); // Fix state initialization
  const [email, setEmail] = useState(""); // Fix variable name
  const navigate = useNavigate(); // Navigation hook
const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  
useEffect(() => {
          // Retrieve user data from localStorage
          const storedUser = localStorage.getItem("user");
          const storedToken = localStorage.getItem("token");
          
          if (storedUser) {
              setUser(JSON.parse(storedUser));
              setToken(storedToken);
             
          } else {
              // Redirect to login if not logged in
              navigate("/");
          }
      }, [navigate]);

      useEffect(() => {
        if (user && token) {
          fetchProfile();
        }
      }, [user, token]);

      function fetchProfile() {
        if (!user?.id) {
          console.error("User data is missing");
          return;
        }
      
        axios
          .get(`http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/users/${user.id}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          })
          .then((res) => {
            if (res.data) {
              setName(res.data.name);
              setEmail(res.data.email);
            } else {
              console.error("Invalid response format:", res.data);
            }
          })
          .catch((err) => {
            alert("Fetching failed: " + err.message);
          });
      }
      

  const goHome = () => {
    navigate("/home"); // Navigate to home
  };

  return (
    <div className="container">
      <button className="back-button" onClick={goHome}>← Home</button>
      <h2>Player Profile</h2>
      <div className="profile-card">
        <h3 id="player-name">Player Name: {name || "Loading..."}</h3>
        <p>Email: <span id="player-email">{email || "Loading..."}</span></p>
      </div>
    </div>
  );
}
