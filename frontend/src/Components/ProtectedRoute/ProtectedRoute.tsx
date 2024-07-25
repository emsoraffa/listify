
import { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext/AuthContext";

interface ProtectedRouteProps {
  element: ReactNode;
}

export const ProtectedRoute = ({ element }: ProtectedRouteProps) => {
  const { isAuthenticated } = useAuth(); // Destructure to get isAuthenticated from useAuth hook

  if (!isAuthenticated) {
    // User is not authenticated
    return <Navigate to="/login" />;
  }

  return <>{element}</>;
};

