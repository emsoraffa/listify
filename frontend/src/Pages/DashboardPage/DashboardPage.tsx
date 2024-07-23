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
  //TODO: handle empty dashboard
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });
  const [lists, setLists] = useState<DashboardListDto[]>([]); // State to store the fetched data
  const authState = useContext(AuthContext);

  useEffect(() => {
    if (authState?.token) {
      fetchUserLists(authState.token)
        .then(lists => {
          setLists(lists)
          console.log("Fetched lists: ")
          console.log(lists)
        })  // Store fetched data in state
        .catch((err) => console.log(err.message));

    }
    else navigate("/login");
  }, [authState?.token, navigate]);

  const handleCardClick = (id: number) => {
    navigate(`/li/${id}`)
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
      )}    </div>
  );
}
