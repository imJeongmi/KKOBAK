import React from "react";

import './Textarea.scss'

const textAreaStyle = {
  resize: "none",
}
export default function Textarea({ type, placeholder, onChange }) {
  return (
    <label className="label">
      <textarea className="textarea" style={textAreaStyle} rows="5" type={type} placeholder={placeholder} onChange={onChange} />
    </label>
  );
}
