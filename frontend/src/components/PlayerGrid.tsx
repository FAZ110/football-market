import React from 'react'
import type Player from '../types';
import PlayerCard from './PlayerCard';

interface PlayerGridProps {
    players: Player[];
}

const PlayerGrid: React.FC<PlayerGridProps> = ({players}) => {
  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 max-w-7xl mx-auto">
        {players.map((player) => (
            // Klucz (key) jest wymagany przez React podczas mapowania list
            // Przekazujemy pojedynczego zawodnika do propa "info"
            <PlayerCard key={player.id} info={player} />
        ))}
    </div>
  )
}

export default PlayerGrid