import { ReactNode } from "react";
import { Navigate } from "react-router-dom";

interface ProtectedRouteProps {
  element: ReactNode;
}
export const ProtectedRoute = ({ element }: ProtectedRouteProps) => {
  const user = localStorage.getItem('accessToken'); // Assuming user authentication is stored in localStorage
  if (!user) {
    // user is not authenticated
    return <Navigate to="/login" />;
  }
  return <>{element}</>;
};
