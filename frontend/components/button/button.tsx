function Button() {
  const handleClick = () => {
    console.log('Button clicked');
  };
  return (
    <button className="button" onClick={() => handleClick()}>
    </button>
  );
}
