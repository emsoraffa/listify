import { Route, Routes } from 'react-router-dom';
import { ListifyOutlet } from './Components/ListifyOutlet';
import { NavBar } from './Components/NavBar';
import { HomePage } from './Pages/HomePage';

export function AppRoutes() {
  return (
    <Routes>
      <Route element={<ListifyOutlet />}>

        <Route path="/" element={<HomePage />} />
      </Route>
    </Routes>
  );
}
