import React, { useEffect, useState } from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import { useMediaQuery } from 'react-responsive';
import { DashboardListDto } from '../../dto';
import { fetchUserLists } from '../../api';
import { ListCard } from '../../Components';
import { useAuth } from '../../context/AuthContext/AuthContext';

export function DashboardPage() {
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });
  const [lists, setLists] = useState<DashboardListDto[]>([]); // State to store the fetched data
  const { token, isAuthenticated } = useAuth();

  useEffect(() => {
    if (isAuthenticated && token) {
      fetchUserLists(token)
        .then(lists => {
          setLists(lists);
          console.log("Fetched lists: ", lists);
        })  // Store fetched data in state
        .catch((err) => console.log(err.message));
    } else {
      navigate("/login");
    }
  }, [token, isAuthenticated, navigate]);

  const handleCardClick = (id: number) => {
    navigate(`/list/${id}`);
  }
  const handleNewListClick = () => {
    navigate(`/list/new`);
  }

  return (
    <div className="content-container">
      <h2>My lists</h2>
      {lists.length > 0 ? (
        lists.map((list) => (
          <ListCard
            key={list.list_id} // Use list_id as key
            list_name={list.list_name}
            author={list.author}
            onClick={() => handleCardClick(list.list_id)} // Pass a function to onClick
          />
        ))
      ) : (
        <div className="empty-dashboard-text">So empty...</div>
      )}
      <Button variant="contained" color="primary" onClick={handleNewListClick}>
        Create New List
      </Button>
    </div>
  );
}

