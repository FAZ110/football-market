import './App.css'
import { useState, useEffect } from 'react';
import axios from 'axios';
import type Player from './types';
import PlayerGrid from './components/PlayerGrid';
import SearchBar from './components/SearchBar';

function App() {
  const [players, setPlayers] = useState<Player[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [searchTerm, setSearchTerm] = useState<string>('');
  const [pages, setPages] = useState<number>(1);

  useEffect(() => {
    const fetchPlayers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/players');
        setPlayers(response.data);
      } catch (err) {
        console.error('Error fetching data:', err);
        setError('Fetching players failed.');
      } finally {
        // 2. Ten kod wykona się zawsze (sukces lub błąd), zdejmując stan ładowania
        setLoading(false);
      }
    };

    fetchPlayers();
  }, []);

  const filteredPlayers = players.filter((player) => {
    const fullName = `${player.firstName} ${player.lastName}`.toLowerCase();
    return fullName.includes(searchTerm.toLowerCase());
  })

  const playersList = filteredPlayers.slice(0, pages*20)

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-900 text-white">
        <p className="text-xl font-bold animate-pulse">Market is loading...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-900 text-red-500">
        <p className="text-xl font-bold">{error}</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-900 p-8 text-gray-100">
      <header className="mb-10 text-center">
        <h1 className="text-4xl font-extrabold text-transparent bg-clip-text from-green-400 to-blue-500 mb-2">
          Football Market
        </h1>
        <p className="text-gray-400">Available Players: {players.length}</p>
      </header>
      <SearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm}/>
      <PlayerGrid players={playersList} />

      {filteredPlayers.length === 0 && (
        <div className="text-center text-gray-500 mt-10">
          Player not found
        </div>
      )}
      {filteredPlayers.length > playersList.length && (
        <div className="flex justify-center mt-12 mb-8">
          <button 
            onClick={() => setPages(pages + 1)}
            className="px-8 py-3 bg-blue-600 hover:bg-blue-500 text-white font-bold rounded-full transition-colors shadow-lg shadow-blue-500/30 active:scale-95"
          >
            Show more
          </button>
        </div>
      )}

    </div>
  );
}

export default App;