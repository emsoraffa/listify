
import React, { createContext, useState, useEffect, ReactNode, useContext } from 'react';
import { fetchUserDetails } from '../../api';
import { useAuth } from '../AuthContext/AuthContext';

interface User {
  name: string;
  email: string;
}

interface UserContextType {
  user: User | null;
  setUser: (user: User | null) => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

const UserProvider = ({ children }: { children: ReactNode }) => {
  const { token, isAuthenticated } = useAuth(); // Invoke the hook
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    if (token && isAuthenticated) {
      fetchUserDetails(token)
        .then((data) => setUser(data))
        .catch(error => console.error('Error while attempting to set User', error));
    }
  }, [token, isAuthenticated]);

  return (
    <UserContext.Provider value={{ user, setUser }}>
      {children}
    </UserContext.Provider>
  );
};

// Custom hook to use the UserContext
const useUser = (): UserContextType => {
  const context = useContext(UserContext);
  if (context === undefined) {
    throw new Error('useUser must be used within a UserProvider');
  }
  return context;
};

export { UserProvider, useUser };

