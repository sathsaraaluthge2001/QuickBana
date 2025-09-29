import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/style-levels.css"; 

export default function LevelHandle() {
  const [selectedLevel, setSelectedLevel] = useState(null);
  const [levels, setLevels] = useState([]);
  const [completeLevels, setCompleteLevels] = useState([]);
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);
  const [popupMessage, setPopupMessage] = useState(""); 
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
    if (token) { 
      fetchLevels();
      fetchCanAccessLevels();
    }
  }, [token]); 
  

  function fetchLevels() {
    axios
      .get("http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/levels", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        if (res.data && Array.isArray(res.data)) {
          setLevels(res.data);
        } else {
          console.error("Invalid response format:", res.data);
        }
      })
      .catch((err) => {
        alert("Fetching levels failed: " + err.message);
      });
  }

  function fetchCanAccessLevels() {
    if (!user) return;
    axios
  .get(`http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/user-progress/complete-level/${user.id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  .then((res) => {
    if (res.data && Array.isArray(res.data)) {
      const levelNumbers = res.data.map(item => item.levelNumbers);
      setCompleteLevels(levelNumbers);  // Assuming setCompleteLevels expects an array of numbers
      console.log("Processed levels:", levelNumbers);
    } else {
      console.error("Invalid response format:", res.data);
    }
  })
  .catch((err) => {
    alert("Fetching completed levels failed: " + err.message);
  });

  }

  // Determine the highest completed level and allow one more level
  const highestCompletedLevel = completeLevels.length > 0 ? Math.max(...completeLevels) : 0;
  const maxAccessibleLevel = highestCompletedLevel + 1;

  const startLevel = (level) => {
    if (level.level_number > maxAccessibleLevel) {
      setPopupMessage(`You need to complete Level ${maxAccessibleLevel} first to unlock this level.`);
      return;
    }
    setSelectedLevel(level);
    alert(`Starting Level ${level.level_number} with ${level.num_images} images in ${level.time_limit} seconds`);
    navigate(`/game/${level.id}/${level.time_limit}`, { state: { levelId: level.id } });
  };

  return (
    <div className="container">
      <button className="back-button" onClick={() => navigate("/home")}>← Home</button>
      <h2>Select a Level</h2>
      <div className="level-buttons">
        {levels.length > 0 ? (
          levels.map((level) => (
            <button 
              key={level.id} 
              onClick={() => startLevel(level)}
              className={level.level_number > maxAccessibleLevel ? "locked-level" : ""}
            >
              Level {level.level_number} - {level.num_images} Images ({level.time_limit}s)
            </button>
          ))
        ) : (
          <p>Loading levels...</p>
        )}
      </div>

      {/* Popup Message */}
      {popupMessage && (
        <div className="popup">
          <div className="popup-content">
            <p>{popupMessage}</p>
            <button onClick={() => setPopupMessage("")}>OK</button>
          </div>
        </div>
      )}
    </div>
  );
}
