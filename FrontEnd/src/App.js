import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginHandle from './Components/sign-in';
import RegisterHandle from './Components/sign-up';
import LevelHandle from "./Components/Level";
import GameHandle from "./Components/game";
import Home from "./Components/home";
import LeaderBoard from "./Components/leaderboard";
import Profile1Handel from "./Components/profile"
import GambHandle from "./Components/gamb";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginHandle />} />
        <Route path="/sign-up" element={<RegisterHandle />} />
        <Route path="/levels" element={<LevelHandle />} />
        <Route path="/home" element={<Home />} />
        <Route path="/game/:levelId/:levelTime" element={<GameHandle />} />
        <Route path="/leader-board" element={<LeaderBoard />} />
        <Route path="/profile" element={<Profile1Handel />} />
        <Route path="/gm" element={<GambHandle />} />
      </Routes>
    </Router>
  );
}

export default App;
