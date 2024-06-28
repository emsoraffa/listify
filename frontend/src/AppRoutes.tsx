import { Route, Routes } from 'react-router-dom';
import { ListifyOutlet } from './Components/ListifyOutlet';
import { OAuth2RedirectHandler } from './Components/OAuth2RedirectHandler';
import { ProtectedRoute } from './Components/ProtectedRoute';
import { HomePage, ListPage, LoginPage } from './Pages/';
import { DashboardPage } from './Pages/DashboardPage';


export function AppRoutes() {
  return (
    <Routes>
      <Route element={<ListifyOutlet />}>
        <Route path="/test" element={<ListPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route
          path="/dashboard"
          element={<ProtectedRoute element={<DashboardPage />} />}
        />
        <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
      </Route>
    </Routes>
  );
}
