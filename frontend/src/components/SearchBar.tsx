import React, { useState } from 'react'

interface SearchBarProps {
    searchTerm: string
    setSearchTerm: (term: string) => void
}

const SearchBar: React.FC<SearchBarProps> = ({searchTerm, setSearchTerm}) => {


  return (
    <div className="mb-10 flex justify-center items-center flex-col w-full">
        <input 
            type="text" 
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full max-w-md px-5 py-3 rounded-full bg-gray-800 border border-gray-700 text-white placeholder-gray-400 focus:outline-none focus:border-blue-500 focus:ring-1 focus:ring-blue-500 transition-all shadow-lg" 
            placeholder="Search by first name or last name..." 
        />
    </div>
  )
}

export default SearchBar