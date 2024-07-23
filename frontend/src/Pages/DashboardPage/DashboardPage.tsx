import React, { useContext, useEffect, useState } from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import { useMediaQuery } from 'react-responsive';
import { DashboardListDto, ListDto } from '../../dto';
import { fetchUserLists } from '../../api';
import { AuthContext } from '../../context/AuthContext';
import { ListCard } from '../../Components';



export function DashboardPage() {
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });
  const [lists, setLists] = useState<DashboardListDto[]>([]); // State to store the fetched data
  const authState = useContext(AuthContext);

  useEffect(() => {
    if (authState?.token) {
      fetchUserLists(authState.token)
        .then(list => setLists(list))  // Store fetched data in state
        .catch((err) => console.log(err.message));

      console.log(lists);

    }
    else navigate("/login");
  }, []);

  return (
    <div className="content-container">
      <h2>My lists</h2>
      {/* Render each ListDto's author and list items */}
      {lists.map((list, index) => (
        <ListCard list_name={list.list_name} author={list.author} />
      ))}
    </div>
  );
}
