import React, { useState } from "react";
import './App.css';

function App() {
  const [fileName, setFileName] = useState("");
  const [results, setResults] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  // Handle input change
  const handleInputChange = (event) => {
    setFileName(event.target.value);
  };

  // Handle search button click
  const handleSearch = async () => {
    if (!fileName) {
      setError("Please enter a file name.");
      return;
    }

    setError(null);
    setLoading(true);
    try {
      const response = await fetch(`http://localhost:8080/search?file_name=${fileName}`);
      if (!response.ok) {
        throw new Error("Failed to fetch results");
      }
      const data = await response.json();
      setResults(data);  // Assume the response is a list of file records
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="App">
      <h1>Search for Files</h1>
      
      <input 
        type="text" 
        value={fileName} 
        onChange={handleInputChange} 
        placeholder="Enter file name..." 
      />
      
      <button onClick={handleSearch} disabled={loading}>
        {loading ? "Searching..." : "Search"}
      </button>
      
      {error && <p>{error}</p>}

      <ul>
        {results.length > 0 ? (
          results.map((record, index) => (
            <li key={index}>
              <strong>File Path:</strong> {record.file_path} <br />
              <strong>Extension:</strong> {record.extension} <br />
              <strong>File Size:</strong> {record.file_size} bytes <br />
              <strong>Lines:</strong> {record.lines} <br />
              <strong>Content:</strong> {record.content} <br />
            </li>
          ))
        ) : (
          <li>No results found</li>
        )}
      </ul>
    </div>
  );
}

export default App;
