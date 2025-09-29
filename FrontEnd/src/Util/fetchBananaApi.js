import axios from "axios";

export const fetchBananaApi = async () => {
  try {
    const response = await axios.get(
        "http://marcconrad.com/uob/banana/api.php"
    );

    const parsedData = JSON.parse(response.data.contents);

    if (parsedData.question && parsedData.solution) {
      return {
        question: parsedData.question,
        solution: parsedData.solution,
      };
    } else {
      throw new Error("Invalid API response");
    }
  } catch (error) {
    throw new Error("Failed to load question: " + error.message);
  }
};
