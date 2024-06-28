import React, { createContext, useState, useEffect, ReactNode } from 'react';

interface AuthContextType {
  isAuthenticated: boolean;
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [authState, setAuthState] = useState<{ isAuthenticated: boolean; token: string | null }>({
    isAuthenticated: false, token: null,
  }); useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      setAuthState({ isAuthenticated: true, token });
    }
  }, []);

  const login = (token: string) => {
    localStorage.setItem('accessToken', token);
    setAuthState({ isAuthenticated: true, token });
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    setAuthState({ isAuthenticated: false, token: null });
  };

  return (
    <AuthContext.Provider value={{ ...authState, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export { AuthContext, AuthProvider };
