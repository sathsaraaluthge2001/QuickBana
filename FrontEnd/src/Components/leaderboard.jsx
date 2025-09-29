import React, { useState, useEffect } from "react";
import axios from "axios";
import "../css/style-scoreboard.css"; // Ensure this file is properly set up
import { useNavigate } from "react-router-dom";
export default function LeaderBoard() {
  const [scores, setScores] = useState([]);
const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
      const storedUser = localStorage.getItem("user");
      const storedToken = localStorage.getItem("token");
      if (storedUser) {
        setUser(JSON.parse(storedUser));
        setToken(storedToken);
      } else {
        navigate("/");
      }
    }, [navigate]);

  useEffect(() => {
    fetchScores();
  }, [token]);

  function fetchScores() {
    if (!token) {
      console.error("Token not available, retrying...");
      return;
  }
    axios
      .get("http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/user-progress/score-board", {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        if (res.data && Array.isArray(res.data)) {
          setScores(res.data);
        } else {
          console.error("Invalid response format:", res.data);
        }
      })
      .catch((err) => {
        alert("Fetching failed: " + err.message);
      });
  }

  function goHome() {
    window.location.href = "/home"; // Adjust this URL based on your routing
  }

  return (
    <div className="container">
      <button className="back-button" onClick={goHome}>← Home</button>
      <h2>Scoreboard</h2>
      <table>
        <thead>
          <tr>
            <th>Rank</th>
            <th>Player</th>
            <th>Score</th>
          </tr>
        </thead>
        <tbody>
          {scores.length > 0 ? (
            scores.map((player, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{player.name}</td>
                <td>{player.score}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3">No scores available</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
