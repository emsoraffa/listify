import { Route, Routes } from 'react-router-dom';
import { HomePage, ListPage, LoginPage, DashboardPage } from './Pages/';
import { ProtectedRoute, SidebarOutlet, NavbarOutlet, OAuth2RedirectHandler } from './Components/';

export function AppRoutes() {
  const isMobileTest = process.env.REACT_APP_MOBILE_TEST === 'true';

  return (
    <Routes>
      <Route element={<NavbarOutlet />}>
        <Route path="/login" element={<LoginPage isMobileTest={isMobileTest} />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler />} />
      </Route>

      <Route element={<SidebarOutlet />}>

        <Route path="/li/:id" element={<ProtectedRoute element={<ListPage />} />} />
        <Route
          path="/dashboard"
          element={<ProtectedRoute element={<DashboardPage />} />}
        />

      </Route>
    </Routes>

  );
}
