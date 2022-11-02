import { Box } from '@mui/system';
import { getCategoryList } from 'api/Category';
import React from 'react';
import { useState } from 'react';
import { useEffect } from 'react';

export default function ChallengeCategory() {
  const [category, setCategory] = useState();
  useEffect(() => {
    getCategoryList(getCategoryListSuccess, getCategoryListfail)
    }, [])
  
  function getCategoryListSuccess(res) {
    setCategory(res.data)
    console.log(res.data)
  }

  function getCategoryListfail(err) {
    console.log(err)
  } 

return (
  <Box></Box>
)
}