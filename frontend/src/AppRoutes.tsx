import { Route, Routes } from 'react-router-dom';
import { ListifyOutlet } from './Components/ListifyOutlet';
import { OAuth2RedirectHandler } from './Components/OAuth2RedirectHandler';
import { HomePage, ListPage, LoginPage } from './Pages/';

export function AppRoutes() {
  return (
    <Routes>
      <Route element={<ListifyOutlet />}>
        <Route path="/test" element={<ListPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
      </Route>
    </Routes>
  );
}
