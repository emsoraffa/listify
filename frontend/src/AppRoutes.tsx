import { Route, Routes } from 'react-router-dom';
import { ListifyOutlet } from './Components/ListifyOutlet';
import { HomePage, ListPage } from './Pages/';

export function AppRoutes() {
  return (
    <Routes>
      <Route element={<ListifyOutlet />}>
        <Route path="/test" element={<ListPage />} />
        <Route path="/" element={<HomePage />} />
      </Route>
    </Routes>
  );
}
