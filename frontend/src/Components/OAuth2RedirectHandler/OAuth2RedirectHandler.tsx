import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export function OAuth2RedirectHandler() {
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get('token');
    console.log('OAuth2 Redirect Handler called with token:', token); // Debug log

    if (token) {
      localStorage.setItem('accessToken', token);
      navigate('/home');
    } else {
      console.error('Token is null');
      navigate('/login');
    }
  }, [navigate]);

  return <div>Loading...</div>;
};

