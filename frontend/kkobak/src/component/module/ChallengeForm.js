import { Box } from '@mui/system';
import React from 'react';

export default function ChallengeBasicForm() {
	const challengeImgInput = []
	function onImgChange(e) {
		e.preventDefault()
	}

	return (
		<Box>
			<input ref={challengeImgInput} type='file' accept='image/*' onChange={onImgChange}></input>
		</Box>
	)
}