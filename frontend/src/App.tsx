import './App.css'
import { useState, useEffect } from 'react';
import axios from 'axios';
import type Player from './types';
import PlayerGrid from './components/PlayerGrid';

function App() {
  const [players, setPlayers] = useState<Player[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // 1. Definiujemy asynchroniczną funkcję pobierającą
    const fetchPlayers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/players');
        setPlayers(response.data);
      } catch (err) {
        console.error('Błąd pobierania danych:', err);
        setError('Nie udało się pobrać zawodników. Upewnij się, że backend działa!');
      } finally {
        // 2. Ten kod wykona się zawsze (sukces lub błąd), zdejmując stan ładowania
        setLoading(false);
      }
    };

    // 3. Natychmiast wywołujemy naszą funkcję
    fetchPlayers();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen bg-gray-900 text-white">
        <p className="text-xl font-bold animate-pulse">Trwa pobieranie rynku transferowego...</p>
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

      <PlayerGrid players={players} />
    </div>
  );
}

export default App;