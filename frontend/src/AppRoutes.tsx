import { Route, Routes } from 'react-router-dom';
import { HomePage, ListPage, LoginPage, DashboardPage } from './Pages/';
import { ProtectedRoute, SidebarOutlet, NavbarOutlet, OAuth2RedirectHandler } from './Components/';

export function AppRoutes() {
  return (
    <Routes>
      <Route element={<NavbarOutlet />}>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
      </Route>

      <Route element={<SidebarOutlet />}>

        <Route path="/li" element={<ProtectedRoute element={<ListPage />} />} />
        <Route
          path="/dashboard"
          element={<ProtectedRoute element={<DashboardPage />} />}
        />

      </Route>
    </Routes>

  );
}
