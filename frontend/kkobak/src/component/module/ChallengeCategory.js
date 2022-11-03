import { Box } from '@mui/system';
import { getCategoryList } from 'api/Category';
import ImageButton from 'component/atom/ImageButton';
import MainBox from 'component/atom/MainBox';
import React from 'react';
import { useState } from 'react';
import { useEffect } from 'react';

const BoxStyle = {
  height: "100vh",
  width: "70vw",
  display: "flex",
  // alignItems: "center",
  flexDirection: "column",
};

const CardStyle = {
  width: "95%",
  margin: "auto",
  minHeight: "80vh",
  backgroundColor: "#F5F5F5",
  textAlign: "center",
  display: "flex",
  justifyContent: "space-around",
  borderRadius: "20px",
};


export default function ChallengeCategory({ setCategory }) {
  const [categoryList, setCategoryList] = useState([]);

  useEffect(() => {
    getCategoryList(getCategoryListSuccess, getCategoryListfail)
  }, [])

  function getCategoryListSuccess(res) {
    setCategoryList(res.data)
    console.log(res.data)
  }

  function getCategoryListfail(err) {
    console.log(err)
  }

  return (
    <Box sx={BoxStyle}>
      <Box sx={CardStyle}>
        <ImageButton src={categoryList[0]?.imgurl} size="l" id={1} onClick={(e) => setCategory(e.target.id)}>운동</ImageButton>
        <ImageButton src={categoryList[1]?.imgurl} size="l" id={2} onClick={(e) => setCategory(e.target.id)}>생활습관</ImageButton>
      </Box>
    </Box >
  )
}