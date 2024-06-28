import React, { useEffect, useState } from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import { useMediaQuery } from 'react-responsive';
import { getListItems } from '../../api';


interface ListItem {
  name: string;
}
export function DashboardPage() {
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' })

  const [greeting, setGreeting] = useState<ListItem | null>(null);

  useEffect(() => {
    getListItems()
      .then(setGreeting)
      .catch((err) => console.log(err.message));
  }, []);


  return (
    <div className="content-container">
      <div>{greeting ? greeting.name : "Loading..."}</div>

    </div>
  );
}

