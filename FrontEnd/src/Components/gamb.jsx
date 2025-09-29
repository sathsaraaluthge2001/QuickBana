import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate, useLocation, useParams } from "react-router-dom";
import "../css/style-game.css";

export default function GameHandle() {

  const { levelId } = useParams();
  const [gameImage, setGameImage] = useState("");
  const [solution, setSolution] = useState(null);
  const [userAnswer, setUserAnswer] = useState("");
  const [user, setUser] = useState(null);
  const [message, setMessage] = useState("");
  const [gameKey, setGameKey] = useState(0);
  const [previousQuestion, setPreviousQuestion] = useState("");
  const [gcount, setcount] = useState(0);
  const [canAnswer, setCanAnswer] = useState(true);
  const [showPopup, setShowPopup] = useState(false);
  const [timeLeft, setTimeLeft] = useState(300); // 5-minute timer

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

  const goLevels = () => {
    navigate("/levels");
  };

  useEffect(() => {
    fetchNewQuestion();

    const timer = setInterval(() => {
      setTimeLeft((prevTime) => {
        if (prevTime <= 1) {
          clearInterval(timer);
          setCanAnswer(false); // Disable answering when time runs out
          return 0;
        }
        return prevTime - 1;
      });
    }, 1000);

    return () => clearInterval(timer);
  }, []);

  function fetchNewQuestion() {
    axios
      .get(
        `https://api.allorigins.win/get?url=${encodeURIComponent(
          "http://marcconrad.com/uob/banana/api.php?out=json&base64=no"
        )}&t=${new Date().getTime()}`
      )
      .then((res) => {
        console.log(res.data.contents);
        const parsedData = JSON.parse(res.data.contents);
        if (parsedData.question && parsedData.solution) {
          if (parsedData.question === previousQuestion) {
            fetchNewQuestion();
            return;
          }

          setGameImage(parsedData.question);
          setSolution(parsedData.solution);
          setMessage("");
          setUserAnswer("");
          setGameKey((prevKey) => prevKey + 1);
          setPreviousQuestion(parsedData.question);
          setCanAnswer(timeLeft > 0); // Keep answering enabled only if time is left
        } else {
          setMessage("❌ API returned an invalid response.");
        }
      })
      .catch((err) => {
        setMessage("❌ Failed to load question.");
      });
  }

  function handleNumberClick(num) {
    if (!canAnswer) return;

    setUserAnswer(num);
    sendResult(num);
  }

  function sendResult(answer) {
    console.log(levelId);
    axios

      .post("http://127.0.0.1:8080/quickbana-0.0.1-SNAPSHOT/api/game", null, {
        params: {
          userId: user.id,
          levelId: levelId,
          solution: solution,
          answer: answer,
          count: gcount,
          time: timeLeft,
        },
      })
      .then((res) => {
        let respo = res.data.response;
        if (respo == 1) {
          setMessage("✅ Correct Answer!");
          setcount(res.data.count);
          if (res.data.finish) {
            setMessage("🎉 Level Up! Congratulations! 🎉");
            setShowPopup(true);
          } else {
            setTimeout(() => fetchNewQuestion(), 2000);
          }
        } else {
          setMessage("❌ Wrong Answer!");
        }
      })
      .catch((err) => {
        alert("Update failed: " + err.message);
      });
  }

  const goToNextLevel = () => {
    setShowPopup(false);
    navigate(`/levels`);
  };

  return (
    <div className="container">
      <button className="back-button" onClick={goLevels}>
        ← Levels
      </button>
      <h2>Guess the Number</h2>
      <div id="image-container">
        <img id="game-image" src={gameImage} alt="Loading..." />
      </div>
      <div id="timer">
        Time Left: <span>{timeLeft}</span> sec
      </div>
      <div id="number-buttons">
        {[0, 1, 2, 3, 4, 5, 6, 7, 8, 9].map((num) => (
          <button key={num} onClick={() => handleNumberClick(num)} disabled={!canAnswer}>
            {num}
          </button>
        ))}
      </div>
      <div id="result">{message}</div>

      {showPopup && (
        <div className="popup">
          <div className="popup-content">
            <h3>🎉 Level Up! 🎉</h3>
            <p>Congratulations! You have completed this level.</p>
            <button onClick={goToNextLevel}>Go to Next Level</button>
          </div>
        </div>
      )}
    </div>
  );
}