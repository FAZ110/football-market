import React, { useState } from 'react'
import type Player from '../types'

interface PlayerProps {
    info: Player
}

const PlayerCard:React.FC<PlayerProps> = ({info}) => {


    // const [expanded, setExpanded] = useState<boolean>(false)

  return (
    <div className="bg-gray-500 rounded-xl shadow-lg overflow-hidden border border-gray-700 flex flex-col">
        <div className="relative pt-4 flex justify-center bg-gray-700/50">
            <img src={info.photoUrl} alt={`${info.firstName} ${info.lastName}`} 
            className='w-32 h-32 object-contain rounded-2xl'
            onError={(e) => { e.currentTarget.src = 'https://via.placeholder.com/150?text=Brak+zdjęcia'; }}/>

            <img 
                src={info.teamLogo} 
                alt={info.teamName} 
                className="absolute top-2 right-2 w-8 h-8 object-contain drop-shadow-md"
              />
        </div>

        <div className="p-4 grow flex flex-col">
            <h2 className='text-lg font-bold truncate'>
                {info.firstName} <span className='uppercase'>{info.lastName}</span>
            </h2>

            <div className="flex justify-between items-center mt-2 text-sm text-gray-400">
                <span>Age: {info.age}</span>
                <span className="bg-gray-700 px-2 py-1 rounded text-xs font-semibold tracking-wider">
                  {info.position}
                </span>
              </div>
              <p className="text-xs text-gray-500 mt-1 truncate">{info.teamName}</p>

              
              <div className="mt-auto pt-4 flex justify-between items-end">
                 <span className="text-sm text-gray-400">Value:</span>
                 <span className="text-xl font-black text-green-400">
                   ${info.price.toFixed(2)}
                 </span>
              </div>
        </div>
    </div>
  )
}

export default PlayerCard