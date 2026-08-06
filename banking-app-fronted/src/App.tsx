import './App.css'
import { Routes, Route } from 'react-router-dom'
import LogInPage from './pages/LogInPage'
import NotFoundPage from './pages/NotFoundPage'

function App() {

  return (
    <>
      <Routes>
        <Route path="/" element={<LogInPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </>
  )
}

export default App
