import Item from "../Items/Item";
import '../home/Home.css';

const MyItemList = ({ itemList, onMyPage, onEdit, onDelete, onEditComplete, setBoardData, boardData }) => {    
    return (
        <div className="MyItemList">
            {itemList.map((e) => (
                <div className="my-item" key={e.boardId}>
                    <Item
                        {...e}
                        onMyPage={onMyPage}
                        onEdit={onEdit}
                        onDelete={onDelete}
                        onEditComplete={onEditComplete}
                        setBoardData={setBoardData}
                        boardData={boardData}
                    />
                </div>                
            ))}
        </div>
    )
}


export default MyItemList;