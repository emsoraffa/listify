import { ReactNode, useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

interface ProtectedRouteProps {
  element: ReactNode;
}
export const ProtectedRoute = ({ element }: ProtectedRouteProps) => {
  const isAuthenticated = useContext(AuthContext);
  if (!isAuthenticated) {
    // user is not authenticated
    return <Navigate to="/login" />;
  }
  return <>{element}</>;
};
