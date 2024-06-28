import React, { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

export function OAuth2RedirectHandler() {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const token = params.get('token');

    console.log('OAuth2 Redirect Handler called with token:', token); // Debug log

    if (token) {
      localStorage.setItem('accessToken', token);
      navigate('/dashboard', { replace: true });
    } else {
      console.error('Token is null');
      navigate('/login', { replace: true });
    }
  }, [location, navigate]);

  return <div>Loading...</div>;
}

