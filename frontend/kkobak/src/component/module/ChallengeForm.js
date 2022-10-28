import { Box } from '@mui/system';
import React from 'react';

export default function ChallengeBasicForm() {
	const challengeImgInput = []
	function onImgChange(e) {
		e.preventDefault()
	}

	return (
		<Box>
			<input style="display: none" ref={challengeImgInput} type='file' accept='image/*' onChange={onImgChange} />
		</Box>
	)
}